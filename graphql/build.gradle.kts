dependencies {
    implementation("io.quarkus:quarkus-smallrye-graphql")
    testImplementation("io.rest-assured:rest-assured")
    testImplementation("io.rest-assured:kotlin-extensions")
}

noArg {
    annotation("io.bsamartins.sandbox.quarkus.graphql.Model")
}
