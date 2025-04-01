package com.parcelpulseapi.service.implementation;

import com.parcelpulseapi.dto.request.ParcelRequest;
import com.parcelpulseapi.dto.response.ParcelResponse;
import com.parcelpulseapi.exception.ResourceNotFoundException;
import com.parcelpulseapi.model.Parcel;
import com.parcelpulseapi.model.User;
import com.parcelpulseapi.repository.ParcelRepository;
import com.parcelpulseapi.service.interfaces.IParcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@Transactional
public class ParcelService implements IParcelService {
    
    private final ParcelRepository parcelRepository;
    private final AuthService authService;

    @Autowired
    public ParcelService(ParcelRepository parcelRepository, AuthService authService) {
        this.parcelRepository = parcelRepository;
        this.authService = authService;
    }

    @Override
    public ParcelResponse createParcel(ParcelRequest request) {
        User currentUser = authService.getCurrentUser();
        Parcel parcel = new Parcel();
        parcel.setUser(currentUser);
        parcel.setTrackingNumber(generateTrackingNumber());
        parcel.setStatus("PENDING");
        updateParcelFromRequest(parcel, request);
        return mapToResponse(parcelRepository.save(parcel));
    }

    @Override
    public List<ParcelResponse> getAllParcels() {
        return parcelRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ParcelResponse> getCurrentUserParcels() {
        User currentUser = authService.getCurrentUser();
        return parcelRepository.findByUserId(currentUser.getId()).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ParcelResponse getParcel(Long id) {
        return mapToResponse(findParcelById(id));
    }

    @Override
    public ParcelResponse getParcelByTrackingNumber(String trackingNumber) {
        return mapToResponse(parcelRepository.findByTrackingNumber(trackingNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Parcel", "tracking number", trackingNumber)));
    }

    @Override
    public ParcelResponse updateParcel(Long id, ParcelRequest request) {
        Parcel parcel = findParcelById(id);
        updateParcelFromRequest(parcel, request);
        return mapToResponse(parcelRepository.save(parcel));
    }

    @Override
    public void deleteParcel(Long id) {
        if (!parcelRepository.existsById(id)) {
            throw new ResourceNotFoundException("Parcel", "id", id);
        }
        parcelRepository.deleteById(id);
    }

    @Override
    public List<ParcelResponse> searchParcels(String status, String startDate, String endDate) {
        LocalDateTime start = null;
        LocalDateTime end = null;
        
        if (startDate != null) {
            start = LocalDate.parse(startDate).atStartOfDay();
        }
        if (endDate != null) {
            end = LocalDate.parse(endDate).atTime(LocalTime.MAX);
        }

        List<Parcel> parcels;
        if (status != null && start != null && end != null) {
            parcels = parcelRepository.findByStatusAndCreatedAtBetween(status, start, end);
        } else if (status != null) {
            parcels = parcelRepository.findByStatus(status);
        } else if (start != null && end != null) {
            parcels = parcelRepository.findByCreatedAtBetween(start, end);
        } else {
            parcels = parcelRepository.findAll();
        }

        return parcels.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private Parcel findParcelById(Long id) {
        return parcelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Parcel", "id", id));
    }

    private void updateParcelFromRequest(Parcel parcel, ParcelRequest request) {
        parcel.setSenderName(request.getSenderName());
        parcel.setSenderAddress(request.getSenderAddress());
        parcel.setReceiverName(request.getReceiverName());
        parcel.setReceiverAddress(request.getReceiverAddress());
        parcel.setWeight(request.getWeight());
        parcel.setDescription(request.getDescription());
    }

    private String generateTrackingNumber() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String random = String.format("%04d", new Random().nextInt(10000));
        return "PP" + timestamp + random;
    }

    private ParcelResponse mapToResponse(Parcel parcel) {
        return new ParcelResponse(
                parcel.getId(),
                parcel.getTrackingNumber(),
                parcel.getSenderName(),
                parcel.getSenderAddress(),
                parcel.getReceiverName(),
                parcel.getReceiverAddress(),
                parcel.getStatus(),
                parcel.getWeight(),
                parcel.getDescription(),
                parcel.getUser().getId(),
                parcel.getCreatedAt(),
                parcel.getUpdatedAt()
        );
    }
}