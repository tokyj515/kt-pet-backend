package com.example.ktpetbackend.code.service;

import com.example.ktpetbackend.code.dto.CodeDto;
import com.example.ktpetbackend.code.dto.CodeGroupDto;
import com.example.ktpetbackend.code.entity.Code;
import com.example.ktpetbackend.code.entity.CodeGroup;
import com.example.ktpetbackend.code.repository.CodeGroupRepository;
import com.example.ktpetbackend.code.repository.CodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CodeService {
    private final CodeRepository codeRepository;
    private final CodeGroupRepository codeGroupRepository;

    public List<CodeGroupDto> selectCodeGroupList() {
        List<CodeGroup> codeGroups = codeGroupRepository.findByActive(1);

        List<CodeGroupDto> codeGroupDtos = new ArrayList<>();

        for (CodeGroup codeGroup : codeGroups) {
            CodeGroupDto codeGroupDto = CodeGroupDto.builder()
                    .codeGroupId(codeGroup.getId())
                    .name(codeGroup.getName())
                    .build();
            codeGroupDtos.add(codeGroupDto);
        }

        return codeGroupDtos;
    }

    public CodeGroupDto selectCodeGroup(Long codeGroupId) {
        CodeGroup codeGroup = codeGroupRepository.findById(codeGroupId).get();
        List<Code> codes = codeRepository.findByActiveAndCodeGroup(1, codeGroup);

        List<CodeDto> codeDtos = new ArrayList<>();

        for (Code code : codes) {
            CodeDto dto = CodeDto.builder()
                    .codeGroupId(codeGroup.getId())
                    .name(code.getName())
                    .codeId(code.getId())
//                    .order(code.getOrder())
                    .build();
            codeDtos.add(dto);
        }

        CodeGroupDto codeGroupDto = CodeGroupDto.builder()
                .codeGroupId(codeGroup.getId())
                .name(codeGroup.getName())
                .codes(codeDtos)
                .build();
        return codeGroupDto;
    }

    public void registerCodeGroup(CodeGroupDto codeGroupDto) {
        CodeGroup codeGroup = CodeGroup.builder()
                .name(codeGroupDto.getName())
                .active(1)
                .build();

        codeGroupRepository.save(codeGroup);
    }

    public void registerCode(CodeDto codeDto) {
        CodeGroup codeGroup = codeGroupRepository.findById(codeDto.getCodeGroupId()).get();

        Code code = Code.builder()
                .name(codeDto.getName())
//                .order(codeDto.getOrder())
                .active(1)
                .codeGroup(codeGroup)
                .build();

        codeRepository.save(code);
    }

    public void modifyCodeGroup(CodeGroupDto codeGroupDto) {
        CodeGroup codeGroup = codeGroupRepository.findById(codeGroupDto.getCodeGroupId()).get();

        codeGroup.setName(codeGroupDto.getName());

        codeGroupRepository.save(codeGroup);
    }

    public void modifyCode(CodeDto codeDto) {
        Code code = codeRepository.findById(codeDto.getCodeId()).get();

        code.setName(codeDto.getName());

        codeRepository.save(code);

    }

    public void deleteCodeGroup(Long codeGroupId) {
        CodeGroup codeGroup = codeGroupRepository.findById(codeGroupId).get();

        List<Code> codes = codeRepository.findByActiveAndCodeGroup(1, codeGroup);

        for (Code code : codes) {
            code.setActive(0);
            codeRepository.save(code);
        }

        codeGroup.setActive(0);
        codeGroupRepository.save(codeGroup);
    }

    public void deleteCode(Long codeId) {
        Code code = codeRepository.findById(codeId).get();
        code.setActive(0);
        codeRepository.save(code);
    }
}
