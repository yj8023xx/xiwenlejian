package com.smallc.xiwenlejian.search.controller;

import com.smallc.xiwenlejian.common.core.controller.BaseController;
import com.smallc.xiwenlejian.common.core.vo.PageResultVO;
import com.smallc.xiwenlejian.search.dto.SearchDTO;
import com.smallc.xiwenlejian.search.service.SearchService;
import com.smallc.xiwenlejian.search.vo.BookVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author yj8023xx
 * @version 1.0
 * @date 2023/6/25
 * @since com.smallc.xiwenlejian.search.controller
 */
@RestController
public class SearchController extends BaseController {

    @Autowired
    private SearchService searchService;

    @GetMapping("/search/{keyword}")
    public ResponseEntity<PageResultVO<BookVO>> searchBooks(@PathVariable String keyword,
                                                            @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                                            @RequestParam(required = false, defaultValue = "12")Integer pageSize) {
        SearchDTO searchDTO = new SearchDTO(keyword, pageNum, pageSize);
        PageResultVO<BookVO> result = searchService.searchBooks(searchDTO);
        return onSuccess(result);
    }

}
