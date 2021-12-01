package io.bsamartins.sandbox.quarkus.graphql

data class GraphQLQuery(val query: String) {
    override fun toString(): String {
        return """{ "query": "$query" }"""
    }
}