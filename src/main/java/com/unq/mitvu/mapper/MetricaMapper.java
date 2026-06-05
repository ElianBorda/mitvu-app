package com.unq.mitvu.mapper;

import com.unq.mitvu.controller.body.FiltroMetricaBodyDTO;
import com.unq.mitvu.controller.dto.DataPointDTO;
import com.unq.mitvu.model.DataPoint;
import com.unq.mitvu.model.FiltroMetrica;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface MetricaMapper {
    FiltroMetrica aFiltroMetrica(FiltroMetricaBodyDTO filtroMetricaBodyDTO);
    List<FiltroMetrica> aListaDeFiltroMetrica(List<FiltroMetricaBodyDTO> filtroMetricaBodyDTOS);

    DataPointDTO aDataPointDTO(DataPoint dataPoint);
    List<DataPointDTO> aListaDeDataPointDTO(List<DataPoint> dataPoints);

}
