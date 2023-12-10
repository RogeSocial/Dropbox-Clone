package com.example.demo.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileDto {
    private String name;
    private byte[] file_content;
}
