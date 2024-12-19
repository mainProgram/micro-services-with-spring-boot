package com.fazeyna.habilitation;

import com.fazeyna.dtos.habilitation.HabilitationRequest;
import com.fazeyna.dtos.habilitation.HabilitationResponse;

import java.util.List;

public interface HabilitationService {

    public List<HabilitationResponse> getAll();

    public HabilitationResponse add(HabilitationRequest request);

    }
