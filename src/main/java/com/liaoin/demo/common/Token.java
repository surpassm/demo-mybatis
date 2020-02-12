package com.liaoin.demo.common;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author mc
 * Create date 2020/2/10 13:19
 * Version 1.0
 * Description
 */
@Builder
@Getter
@Setter
public class Token {
	private Long userId;
	private String key;
	private String token;
}
