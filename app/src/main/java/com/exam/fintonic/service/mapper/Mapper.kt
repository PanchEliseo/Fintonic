package com.exam.fintonic.service.mapper

interface Mapper<I, O> {
    suspend fun map(input: I): O
}