package com.gestion.unidadesoperativas.domain.mapper;

import com.gestion.unidadesoperativas.domain.dto.OperationalUnitRequestDto;
import com.gestion.unidadesoperativas.domain.dto.OperationalUnitResponseDto;
import com.gestion.unidadesoperativas.domain.model.OperationalUnit;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OperationalUnitMapper {

    public static OperationalUnit toModel(OperationalUnitRequestDto dto) {
        return new OperationalUnit(
                dto.getName(),
                dto.getDirector(),
                dto.getPhonenumber(),
                dto.getAddress(),
                dto.getCodubi(),
                dto.getStatus()
        );
    }

    public static OperationalUnit toModel(OperationalUnitRequestDto dto, Integer id_operativeunit) {
        return new OperationalUnit(
                id_operativeunit,
                dto.getName(),
                dto.getDirector(),
                dto.getPhonenumber(),
                dto.getAddress(),
                dto.getCodubi(),
                dto.getStatus()
        );
    }

    public static OperationalUnitResponseDto toDto(OperationalUnit model) {
        return new OperationalUnitResponseDto(
                model.getId_operativeunit(),
                model.getName(),
                model.getDirector(),
                model.getPhonenumber(),
                model.getAddress(),
                model.getCodubi(),
                model.getStatus()
        );
    }

}

