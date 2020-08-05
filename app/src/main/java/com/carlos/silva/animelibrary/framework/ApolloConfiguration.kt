package com.carlos.silva.animelibrary.framework

import com.apollographql.apollo.ApolloClient

object ApolloConfiguration {

    private var INSTANCE: ApolloClient? = null
    fun getInstance(): ApolloClient {
        val tmpApollo = INSTANCE

        if (tmpApollo != null) {
            return tmpApollo
        }

        synchronized(this) {
            val apolloClient = ApolloClient.builder()
                .serverUrl("https://graphql.anilist.co")
                .build()

            INSTANCE = apolloClient
            return apolloClient
        }
    }
}