package com.parcelpulseapi.service.interfaces;

import com.parcelpulseapi.dto.request.ParcelRequest;
import com.parcelpulseapi.dto.response.ParcelResponse;

import java.util.List;

public interface IParcelService {
    ParcelResponse createParcel(ParcelRequest request);
    List<ParcelResponse> getAllParcels();
    List<ParcelResponse> getCurrentUserParcels();
    ParcelResponse getParcel(Long id);
    ParcelResponse getParcelByTrackingNumber(String trackingNumber);
    ParcelResponse updateParcel(Long id, ParcelRequest request);
    void deleteParcel(Long id);
    List<ParcelResponse> searchParcels(String status, String startDate, String endDate);
}