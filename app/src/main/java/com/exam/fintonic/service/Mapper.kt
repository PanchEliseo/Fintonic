package com.exam.fintonic.service

interface Mapper<I, O> {
    suspend fun map(input: I): O
}