package com.tujuhsembilan.app.dto.response;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // untuk otomatis membuat getter dan setter untuk seluruh atribut dibawah
@Builder //untuk membuat constructur dari entitas secara otomatis
@NoArgsConstructor //Anotasi ini membuat konstruktor tanpa argumen untuk kelas yang diannotasi
@AllArgsConstructor //Anotasi ini membuat konstruktor dengan parameter untuk setiap atribut dalam kelas yang diannotasi
public class PositionResponse {
    private UUID positionId; //Akses(modifier) ; tipe data ; nama variabel
    private String positionName;
}
