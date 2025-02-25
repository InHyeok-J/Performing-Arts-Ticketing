package com.performance.web.api.performance.controller

import com.performance.web.api.performance.controller.dto.PerformanceCreateApiRequest
import com.performance.web.api.performance.controller.dto.PerformanceDetailApiResponse
import com.performance.web.api.performance.controller.dto.PerformanceDiscountApiResponse
import com.performance.web.api.performance.controller.dto.PerformancePagingApiResponse
import com.performance.web.api.performance.service.PerformanceService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping("/api/performances")
class PerformanceController(
    private val performanceService: PerformanceService,
) {

    @GetMapping("/{performanceId}")
    fun getPerformance(@PathVariable performanceId: Long): ResponseEntity<PerformanceDetailApiResponse> {
        val result = performanceService.findById(performanceId)
        return ResponseEntity.ok()
            .body(PerformanceDetailApiResponse.from(result))
    }

    /*
    *  공연 정보 리스트 API
    *  필요 정보 : 공연 정보(공연 이름, 가격 정보, 공연 기간, 공연 포스터 ), 전체 페이지 수
    *
    */

    @GetMapping("/list")
    fun getPerformanceByPaging(@RequestParam(defaultValue = "0") page:Int): ResponseEntity<PerformancePagingApiResponse> {
        val result = performanceService.findByPaging(page)
        return ResponseEntity.ok()
            .body(PerformancePagingApiResponse.from(result))
    }


    /*
    *  공연 좌석 할인 정보 API
    */
    @GetMapping("/{performanceId}/discount")
    fun getDiscount(@PathVariable performanceId: Long): ResponseEntity<List<PerformanceDiscountApiResponse>> {
        val result = performanceService.findSeatClassByIdWithDiscounts(performanceId);
        return ResponseEntity.ok()
            .body(result.map { PerformanceDiscountApiResponse.from(it) })
    }


    @PostMapping
    fun createPerformance(@RequestBody performanceCreateApiRequest: PerformanceCreateApiRequest):ResponseEntity<PerformanceDetailApiResponse> {
        val result = performanceService.createPerformance(performanceCreateApiRequest.toCommand())
        val uri = URI.create(result.getId().toString())
        return ResponseEntity.created(uri)
            .body(PerformanceDetailApiResponse.from(result))
    }
}
