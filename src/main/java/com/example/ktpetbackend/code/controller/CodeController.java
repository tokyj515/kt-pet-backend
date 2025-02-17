package com.example.ktpetbackend.code.controller;


import com.example.ktpetbackend.code.dto.CodeDto;
import com.example.ktpetbackend.code.dto.CodeGroupDto;
import com.example.ktpetbackend.code.service.CodeService;
import com.example.ktpetbackend.pet.dto.PetRegisterDto;
import com.example.ktpetbackend.user.entity.ApiResponse;
import com.example.ktpetbackend.user.entity.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/code")
public class CodeController {

    private final CodeService codeService;

    @Operation(summary = "그룹 전체 조회 API")
    @GetMapping("/group/list")
    public ApiResponse<List<CodeGroupDto>> selectCodeGroupList() {
        return new ApiResponse<>(codeService.selectCodeGroupList());
    }


    @Operation(summary = "그룹 상세 조회 + 그룹 내 코드 포함 API")
    @GetMapping("/group/{codeGroupId}")
    public ApiResponse<CodeGroupDto> selectCodeGroup(@PathVariable Long codeGroupId) {
        return new ApiResponse<>(codeService.selectCodeGroup(codeGroupId));
    }


    @Operation(summary = "그룹 등록 API")
    @PostMapping("/group")
    public ApiResponse<String> registerCodeGroup(@RequestBody CodeGroupDto codeGroupDto) {
        codeService.registerCodeGroup(codeGroupDto);
        return new ApiResponse<>("등록에 성공하였습니다.");
    }

    @Operation(summary = "그룹 내 코드 등록 API")
    @PostMapping("")
    public ApiResponse<String> registerCode(@RequestBody CodeDto codeDto) {
        codeService.registerCode(codeDto);
        return new ApiResponse<>("등록에 성공하였습니다.");
    }



    @Operation(summary = "그룹 수정 API")
    @PostMapping("/group/modify")
    public ApiResponse<String> modifyCodeGroup(@RequestBody CodeGroupDto codeGroupDto) {
        codeService.modifyCodeGroup(codeGroupDto);
        return new ApiResponse<>("수정에 성공하였습니다.");
    }

    @Operation(summary = "그룹 내 코드 수정 API")
    @PostMapping("/modify")
    public ApiResponse<String> modifyCode(@RequestBody CodeDto codeDto) {
        codeService.modifyCode(codeDto);
        return new ApiResponse<>("등록에 성공하였습니다.");
    }


    @Operation(summary = "그룹 삭제 API")
    @PostMapping("/group/delete/{codeGroupId}")
    public ApiResponse<String> deleteCodeGroup(@PathVariable Long codeGroupId) {
        codeService.deleteCodeGroup(codeGroupId);
        return new ApiResponse<>("삭제에 성공하였습니다.");
    }

    @Operation(summary = "그룹 내 코드 삭제 API")
    @PostMapping("/delete/{codeId}")
    public ApiResponse<String> deleteCode(@PathVariable Long codeId) {
        codeService.deleteCode(codeId);
        return new ApiResponse<>("삭제에 성공하였습니다.");
    }


}
