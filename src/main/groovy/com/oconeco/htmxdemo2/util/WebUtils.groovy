package com.oconeco.htmxdemo2.util

import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.data.domain.Page
import java.util.stream.IntStream

class WebUtils {
    
    public static final String MSG_SUCCESS = "message_success"
    public static final String MSG_INFO = "message_info"
    public static final String MSG_ERROR = "message_error"
    
    /**
     * Get pagination model for templates
     */
    static Map<String, Object> getPaginationModel(Page<?> page) {
        int totalPages = page.totalPages
        int pageNumber = page.number
        
        int startPage = Math.max(0, pageNumber - 2)
        int endPage = Math.min(totalPages - 1, pageNumber + 2)
        
        List<Integer> pageNumbers = IntStream.rangeClosed(startPage, endPage)
                .boxed()
                .toList()
                
        return [
                'pageNumbers': pageNumbers,
                'currentPage': pageNumber,
                'hasPrevious': page.hasPrevious(),
                'hasNext': page.hasNext(),
                'totalPages': totalPages,
                'showFirst': startPage > 0,
                'showLast': endPage < totalPages - 1
        ]
    }
    
    /**
     * Get localized message
     */
    static String getMessage(String code, Object[] args = null, String defaultMessage = null, MessageSource messageSource = null) {
        if (messageSource == null) {
            // For simplicity, return the code as message if no messageSource is provided
            return args ? "${code} ${args.join(' ')}" : code
        }
        
        return messageSource.getMessage(code, args, defaultMessage, LocaleContextHolder.getLocale())
    }
}
