package br.com.szella.orderapi;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import com.tngtech.archunit.library.Architectures;

@AnalyzeClasses(
        packages = "br.com.szella.orderapi",
        importOptions = {ImportOption.DoNotIncludeTests.class}
)
public class ArchitectureTest {

    // Regra 1: Validação das Camadas da Arquitetura Hexagonal
    @ArchTest
    static final ArchRule hexagonal_layers_must_be_respected = Architectures.layeredArchitecture()
            .consideringAllDependencies()
            .layer("Domain").definedBy("br.com.szella.orderapi.domain..")
            .layer("Application").definedBy("br.com.szella.orderapi.application..")
            .layer("Infrastructure").definedBy("br.com.szella.orderapi.infrastructure..")

            .whereLayer("Domain").mayOnlyBeAccessedByLayers("Application", "Infrastructure")
            .whereLayer("Application").mayOnlyBeAccessedByLayers("Infrastructure");

    // Regra 2: O Domínio NUNCA deve depender de frameworks externos (Spring, JPA, Kafka, Jackson, etc)
    @ArchTest
    static final ArchRule domain_must_be_pure_java = ArchRuleDefinition.noClasses()
            .that().resideInAPackage("..domain..")
            .should().dependOnClassesThat()
            .resideInAnyPackage(
                    "org.springframework..",
                    "jakarta.persistence..",
                    "jakarta.validation..",
                    "org.apache.kafka..",
                    "com.fasterxml.jackson.."
            )
            .because("A camada de domínio deve conter apenas regras de negócio puras em Java.");

    // Regra 3: Controladores REST devem residir apenas em infrastructure.adapters.in.web
    @ArchTest
    static final ArchRule controllers_must_reside_in_web_adapter = ArchRuleDefinition.classes()
            .that().haveSimpleNameEndingWith("Controller")
            .should().resideInAPackage("br.com.szella.orderapi.infrastructure.adapters.in.web..")
            .because("Controllers são adaptadores de entrada HTTP e devem ficar isolados na infraestrutura.");

    // Regra 4: Interfaces de Portas de Saída devem residir em application.ports.out
    @ArchTest
    static final ArchRule output_ports_must_be_interfaces = ArchRuleDefinition.classes()
            .that().resideInAPackage("br.com.szella.orderapi.application.ports.out..")
            .should().beInterfaces()
            .because("Portas de saída definem contratos que a infraestrutura precisa implementar.");
}