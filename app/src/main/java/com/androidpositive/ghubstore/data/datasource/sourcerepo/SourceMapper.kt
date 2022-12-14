package com.androidpositive.ghubstore.data.datasource.sourcerepo

import org.mapstruct.InheritInverseConfiguration
import org.mapstruct.Mapper

@Mapper
interface SourceMapper {
    fun convertToDto(sourceEntity: SourceEntity): SourceDto

    @InheritInverseConfiguration
    fun convertToModel(sourceDto: SourceDto): SourceEntity
}
