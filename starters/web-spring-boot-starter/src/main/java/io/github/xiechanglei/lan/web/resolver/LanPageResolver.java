package io.github.xiechanglei.lan.web.resolver;

import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * <pre>
 * 接口开发中必不可免 需要涉及到分页查询，分页查询的参数一般是page和size,表示第几页和每页多少条数据，
 * 通常情况下，前端传递的参数是page和size，但是如果不传递，我们需要给一个默认值，之前的做法是在controller中获取参数，
 * 然后判断是否为空，如果为空则给默认值，如：
 * <code>
 *     public Result list
 *     (@RequestParam(value = "page", defaultValue= "1" , required = false) Integer page,
 *      \@RequestParam(value = "size", defaultValue = "20", require = true) Integer size) {
 *                   return Result.success();
 *                   }
 * </code>
 * 这样存在冗余代码，不够优雅，我们可以通过自定义参数解析器来实现，这样可以减少冗余代码，提高代码的可读性，
 * 通过使用这种方式，我们最终的代码如下：
 * <code>
 *     public Result list(PageParam size) {
 *     return Result.success();
 *     }
 * </code>
 * 使用一个封装对象PageParam而不是直接使用两个int参数page与size来优化过程，
 * 主要是防止我们再接口中真的需要接受一个page的int参数，该参数又与分页无关，这样就会导致参数解析失败，
 * 如果接口上标注了接受参数的类型是pageParam，那么就不会出现这种问题，因为这样定义，表明了该接口是一个分页查询接口，page和size是分页查询的参数
 *
 * 2024-05-20:
 *      增加sort字段，用于接收前端传递的排序参数，约定规则为：字段名:排序方式，多个字段用逗号分隔：
 *      例如：sort=id:asc,name:desc
 * </pre>
 */
@Component
@RequiredArgsConstructor
public class LanPageResolver implements HandlerMethodArgumentResolver {
    private final LanResolverConfigProperties lanResolverConfigProperties;


    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(PageRequest.class);
    }

    /**
     * 解析分页参数
     */
    @Override
    public PageRequest resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, @NonNull NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        try {
            int pageNo = parsePage(webRequest.getParameter(lanResolverConfigProperties.getPageNoName()));
            int pageSize = parseSize(webRequest.getParameter(lanResolverConfigProperties.getPageSizeName()));
            Sort sort = parseSort(webRequest.getParameter("sort"));
            PageRequest pageRequest = PageRequest.of(pageNo - 1, pageSize);
            pageRequest.withSort(sort);
            return pageRequest;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("分页参数错误,无法解析为数字");
        }
    }

    /**
     * 解析page参数
     *
     * @param pageStr page参数字符串
     * @return page参数
     */
    private int parsePage(String pageStr) {
        if (StringUtils.hasText(pageStr)) {
            int page = Integer.parseInt(pageStr);
            if (page < 1) {
                throw new IllegalArgumentException("分页参数错误,page必须大于等于1");
            }
            return page;
        }
        return lanResolverConfigProperties.getPageNoDefault();
    }

    /**
     * 解析size参数
     *
     * @param sizeStr size参数字符串
     * @return size参数
     */
    private int parseSize(String sizeStr) {
        if (StringUtils.hasText(sizeStr)) {
            int size = Integer.parseInt(sizeStr);
            if (size < 1) {
                throw new IllegalArgumentException("分页参数错误,size必须大于等于1");
            }
            return size;
        }
        return lanResolverConfigProperties.getPageSizeDefault();
    }

    /**
     * 解析sort参数,sort参数格式为：字段名:排序方式，多个字段用逗号分隔
     *
     * @param sortStr sort参数字符串
     * @return sort参数
     */
    private Sort parseSort(String sortStr) {
        Sort sort = null;
        if (StringUtils.hasText(sortStr)) {
            String[] sortArray = sortStr.split(",");
            for (String sortItem : sortArray) {
                String[] sortDetail = sortItem.split(":");
                if (sortDetail.length != 2) {
                    throw new IllegalArgumentException("排序参数错误,排序参数格式为：字段名:排序方式，多个字段用逗号分隔");
                }
                Sort.Direction direction = "asc".equals(sortDetail[1]) ? Sort.Direction.ASC : Sort.Direction.DESC;
                sort = sort == null ? Sort.by(direction, sortDetail[0]) : sort.and(Sort.by(direction, sortDetail[0]));
            }
        }
        return sort == null ? Sort.unsorted() : sort;
    }
}
