package com.androidpositive.ghubstore.data.datasource.sourcerepo

import com.androidpositive.ghubstore.data.datasource.Mapper


class SourceMapper : Mapper<SourceEntity, SourceDto> {
    override fun convertToTarget(source: SourceEntity): SourceDto = SourceDto(source.name)

    override fun convertToSource(target: SourceDto): SourceEntity = SourceEntity(
        uid = 0,
        name = target.name
    )
}
