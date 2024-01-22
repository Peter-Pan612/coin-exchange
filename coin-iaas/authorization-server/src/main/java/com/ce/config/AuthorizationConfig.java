package com.ce.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * @author: Peter
 * @description:
 * @date: 2024/1/22 20:40
 */
@EnableAuthorizationServer //开启授权服务器
@Configuration
public class AuthorizationConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

//    @Autowired
//    private RedisConnectionFactory redisConnectionFactory;

    /**
     * 配置第三方客户端
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("coin-api") //第三方客户端的名称
                .secret(passwordEncoder.encode("coin-secret"))//第三方客户端的密钥
                .scopes("all") //第三方客户端的授权范围
                .accessTokenValiditySeconds(3600)//token的有效期
                .refreshTokenValiditySeconds(7*3600); //refresh_toen的有效期
        super.configure(clients);
    }
    /**
     * 设置授权管理器和UserDetailsService
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService)
                .tokenStore(jwtTokenStore())
                .tokenEnhancer(jwtAccessTokenConverter());
        super.configure(endpoints);
    }

    public TokenStore jwtTokenStore(){
        JwtTokenStore jwtTokenStore = new JwtTokenStore(jwtAccessTokenConverter());
        return jwtTokenStore;
    }

    public JwtAccessTokenConverter jwtAccessTokenConverter(){
        JwtAccessTokenConverter tokenConverter = new JwtAccessTokenConverter();
        //读取classpath下面的密钥文件
        ClassPathResource classPathResource = new ClassPathResource("coinexchange.jks");
        //获取KeyStoreFactory
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(classPathResource,"coinexchange".toCharArray());
        //给JwtAccessToeknConverter设置一个密钥对
        tokenConverter.setKeyPair(keyStoreKeyFactory.getKeyPair("coinexchange","coinexchange".toCharArray()));
        return tokenConverter;
    }

//    public TokenStore redisTokenStore(){
//        return new RedisTokenStore(redisConnectionFactory);
//    }
}
