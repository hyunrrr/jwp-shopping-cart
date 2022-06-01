package woowacourse.auth.acceptance;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.shoppingcart.acceptance.AcceptanceTest2;
import woowacourse.shoppingcart.dto.request.SignUpRequest;

@SuppressWarnings("NonAsciiCharacters")
@DisplayName("인증 관련 기능")
class AuthAcceptanceTest extends AcceptanceTest2 {

    private static final String USERNAME = "유효한_아이디!";
    private static final String PASSWORD = "validPassword!@1";
    private static final String WRONG_PASSWORD = "unvalidPassword!1";

    @BeforeEach
    void setup() {
        회원가입_요청();
    }

    @Test
    void 로그인_성공() {
        TokenRequest 유효한_인증정보 = new TokenRequest(USERNAME, PASSWORD);

        ExtractableResponse<Response> response = 로그인_요청(유효한_인증정보);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void 로그인_실패() {
        TokenRequest 잘못된_인증정보 = new TokenRequest(USERNAME, WRONG_PASSWORD);

        ExtractableResponse<Response> response = 로그인_요청(잘못된_인증정보);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }


    @Test
    void 토큰이_유효한_경우_인가_성공() {
        String 유효한_토큰 = 유효한_로그인_요청();

        ExtractableResponse<Response> response = 내_정보_조회_요청(유효한_토큰);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void 토큰이_유효하지_않은_경우_인가_실패() {
        String 잘못된_토큰 = "안녕하세요.해커입니다.";

        ExtractableResponse<Response> response = 내_정보_조회_요청(잘못된_토큰);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.FORBIDDEN.value());

    }

    private void 회원가입_요청() {
        SignUpRequest newCustomer = new SignUpRequest(USERNAME, PASSWORD, "닉네임", 15);
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(newCustomer)
                .when().post("/customers")
                .then().log().all();
    }

    private String 유효한_로그인_요청() {
        return 로그인_요청(new TokenRequest(USERNAME, PASSWORD))
                .as(TokenResponse.class)
                .getAccessToken();
    }

    private ExtractableResponse<Response> 로그인_요청(TokenRequest request) {
        return RestAssured
                .given().log().all()
                .body(request)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/login")
                .then().log().all().extract();
    }

    private ExtractableResponse<Response> 내_정보_조회_요청(String accessToken) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/customers/me")
                .then().log().all()
                .extract();
    }
}
