package com.ce.controller;

import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiDefinition;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.GatewayApiDefinitionManager;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayFlowRule;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayRuleManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * @author: Peter
 * @description: 获取限流规则
 * @date: 2024/1/22 20:17
 */
@RestController
public class GatewayFlowRulesController {


    /**
     * 获取当前系统的限流策略
     */

    @GetMapping("/gateway")
    public Set<GatewayFlowRule> getGatewayFlowRules(){
        return GatewayRuleManager.getRules();
    }

    /**
     * 获取定义的API分组
     */
    @GetMapping("/api")
    public Set<ApiDefinition> getApiGroupRules(){
        return GatewayApiDefinitionManager.getApiDefinitions();
    }
}
