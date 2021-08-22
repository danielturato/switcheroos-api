package online.switcheroos.api.v1.service;

import lombok.RequiredArgsConstructor;
import online.switcheroos.api.v1.model.AuthenticationAttempt;
import online.switcheroos.api.v1.model.Inet;
import online.switcheroos.api.v1.repository.AuthAttemptRepository;
import online.switcheroos.core.HttpReqRespUtils;
import online.switcheroos.dto.AuthAccountResponseDto;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthAttemptServiceImpl implements AuthAttemptService {

}
