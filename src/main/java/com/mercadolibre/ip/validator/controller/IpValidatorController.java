package com.mercadolibre.ip.validator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mercadolibre.ip.validator.dto.GeneralResponse;
import com.mercadolibre.ip.validator.dto.IpValidatorRequest;
import com.mercadolibre.ip.validator.dto.IpValidatorResponse;
import com.mercadolibre.ip.validator.service.IpValidatorService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/ip")
public class IpValidatorController {
	
	@Autowired
	private IpValidatorService ipValidatorService;
	
	@ApiOperation(value = "Get IP information", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"), @ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 400, message = "Bad Request") })
	@GetMapping("/information/{ip}")
	public ResponseEntity<IpValidatorResponse> getIpInformation(@PathVariable(required = true) String ip){
		return ResponseEntity.ok(ipValidatorService.getIPInformation(ip));
	}
	
	@ApiOperation(value = "Add ip to ban list", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses({ @ApiResponse(code = 201, message = "Created"), @ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 400, message = "Bad Request") })
	@PostMapping("/banned")
	public ResponseEntity<GeneralResponse> addIPToBanList(@RequestBody(required = true) IpValidatorRequest ipValidatorRequest){
		ipValidatorService.addIPToBlackList(ipValidatorRequest.getIpNumber());
		return new ResponseEntity<>(new GeneralResponse("The IP was successfully added to the ban list."), HttpStatus.CREATED);
	}
	
}
