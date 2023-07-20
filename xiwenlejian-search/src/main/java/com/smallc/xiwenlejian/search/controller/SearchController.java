package com.smallc.xiwenlejian.search.controller;

import com.smallc.xiwenlejian.common.core.vo.PageResultVO;
import com.smallc.xiwenlejian.search.dto.SearchDTO;
import com.smallc.xiwenlejian.search.service.SearchService;
import com.smallc.xiwenlejian.search.vo.BookVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author yj8023xx
 * @version 1.0
 * @date 2023/6/25
 * @since com.smallc.xiwenlejian.search.controller
 */
@RestController
public class SearchController {

    @Autowired
    private SearchService searchService;

    @GetMapping("/search")
    PageResultVO<BookVO> searchBooks(@ModelAttribute SearchDTO searchDTO) {
        return searchService.searchBooks(searchDTO);
    }

}
