package com.icfcc.credit.platform.util.jpa;

import java.util.Collection;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import com.google.common.collect.Lists;
import com.icfcc.credit.platform.util.Collections3;

public class DynamicSpecifications
{
    public static <T> Specification<T> bySearchFilter(final Collection<SearchFilter> filters, final Class<T> clazz)
    {
        return new Specification<T>()
        {
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder)
            {
                if (Collections3.isNotEmpty(filters))
                {
                    
                    List<Predicate> predicates = Lists.newArrayList();
                    Predicate predicateLike=null;
                    Predicate predicateLike1=null;
                    Predicate predicateIn=null;
                    for (SearchFilter filter : filters)
                    {
                        // nested path translate, 如Task的名为"user.name"的filedName,
                        // 转换为Task.user.name属性
                        String[] names = StringUtils.split(filter.fieldName, ".");
                        Path expression = root.get(names[0]);
                        for (int i = 1; i < names.length; i++)
                        {
                            expression = expression.get(names[i]);
                        }
                        
                        // logic operator
                        switch (filter.operator)
                        {
                            case EQ:
                                predicates.add(builder.equal(expression, filter.value));
                                break;
                            case EQ1:
                                predicates.add(builder.equal(expression, filter.value));
//                                String[] values = filter.value.toString().split(",");
//                                predicates.add(expression.in(values));
//                                predicates.add( builder.or(builder.equal(expression, filter.value)));
                                break;    
                            case NE:
                                predicates.add(builder.notEqual(expression, filter.value));
                                break;
                            case LIKE:
                            	predicates.add(builder.like(expression, "%" + filter.value + "%"));
                                break;
                            case LIKE1:
                            	predicateLike=builder.like(expression, "%" + filter.value + "%");
                                break;
                            case LIKE2:
                            	predicateLike1=builder.like(expression, "%" + filter.value + "%");
                                break;   
                            case NOTLIKE:
                                predicates.add(builder.notLike(expression, "%" + filter.value + "%"));
                                break;
                            case GT:
                                predicates.add(builder.greaterThan(expression, (Comparable)filter.value));
                                break;
                            case LT:
                                predicates.add(builder.lessThan(expression, (Comparable)filter.value));
                                break;
                            case GTE:
                                predicates.add(builder.greaterThanOrEqualTo(expression, (Comparable)filter.value));
                                break;
                            case LTE:
                                predicates.add(builder.lessThanOrEqualTo(expression, (Comparable)filter.value));
                                break;
                            case IN:
                                String[] values = filter.value.toString().split(",");
                                predicates.add(expression.in(values));
                                break;
                            case NOTIN:
                                String[] values1 = filter.value.toString().split(",");
                                predicates.add(builder.not(expression.in(values1)));
                                break;
                            case ISNULL:
                                predicates.add(builder.isNull(expression));
                                break;
                            case ISNOTNULL:
                                predicates.add(builder.isNotNull(expression));
                                break;
                            case IN1:
                              String[] values2 = filter.value.toString().split(",");
                              predicateIn=expression.in(values2);
                                break;
                        }
                    }
                    if(predicateIn!=null&& predicateLike!=null){
                        predicates.add( builder.or(predicateIn,predicateLike));

                    }else if(predicateLike1!=null&& predicateLike!=null){
                        predicates.add( builder.or(predicateLike1,predicateLike));
                    }else if(predicateIn==null&& predicateLike!=null){
                    	predicates.add( builder.and(predicateLike));
                    }

                    // 将所有条件用 and 联合起来
                    if (!predicates.isEmpty())
                    {
                        return builder.and(predicates.toArray(new Predicate[predicates.size()]));
                    }
                }
                
                return builder.conjunction();
            }
        };
    }
}