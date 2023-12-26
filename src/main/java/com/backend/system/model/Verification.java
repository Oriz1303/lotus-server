package com.backend.system.model;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Verification {
	private boolean status = false;
	private LocalDateTime startedAt;
	private LocalDateTime endAt;
	private String planType;
}
