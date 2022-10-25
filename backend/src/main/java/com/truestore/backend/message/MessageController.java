package com.truestore.backend.message;

import com.truestore.backend.message.dto.CreateMessageDto;
import com.truestore.backend.message.dto.ShortMessageDto;
import com.truestore.backend.security.SecurityUser;
import com.truestore.backend.user.User;
import com.truestore.backend.validation.ValidationErrorBuilder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@Slf4j
@Validated
public class MessageController {

    static final String WRONG_CREDENTIALS = "Wrong credentials";
    private final MessageService messageService;
    private final ModelMapper modelMapper;

    @Autowired
    public MessageController(MessageService messageService, ModelMapper modelMapper) {
        this.messageService = messageService;
        this.modelMapper = modelMapper;
    }

    @Operation(summary = "Create Message to the Contract from Current User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Message is created",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CreateMessageDto.class)) }),
            @ApiResponse(responseCode = "401", description = WRONG_CREDENTIALS,
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Only contract participants can create message",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "Unable to save message",
                    content = @Content)})
    @PostMapping("/messages")
    public ResponseEntity<?> createMessage(
            HttpServletRequest request,
            @RequestBody @Valid CreateMessageDto createMessageDto,
            Errors errors) {
        if (errors.hasErrors()) {
            log.info("Validation error with request: " + request.getRequestURI());
            return ResponseEntity.badRequest().body(ValidationErrorBuilder.fromBindingErrors(errors));
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            Object principal = auth.getPrincipal();
            if (principal instanceof SecurityUser) {
                User user = ((SecurityUser) principal).getUser();
                return new ResponseEntity<>(
                        convertToShortDto(messageService.createMessageFromUserToContract(createMessageDto, user)),
                        HttpStatus.CREATED
                );
            }
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, WRONG_CREDENTIALS);
    }

    ShortMessageDto convertToShortDto(Message message) {
        return modelMapper.map(message, ShortMessageDto.class);
    }
}
