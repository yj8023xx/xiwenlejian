package com.smallc.xiwenlejian.search.service;


import com.smallc.xiwenlejian.common.core.vo.PageResultVO;
import com.smallc.xiwenlejian.search.dto.SearchDTO;
import com.smallc.xiwenlejian.search.vo.BookVO;

import java.util.Map;


/**
 * 服务类
 *
 * @author zhang
 * @version 1.0
 * @since com.smallc.xiwenlejian.service
 */
public interface SearchService {

    PageResultVO<BookVO> searchBooks(SearchDTO searchDTO);

}
