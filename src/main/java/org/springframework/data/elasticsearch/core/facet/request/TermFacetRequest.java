/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.data.elasticsearch.core.facet.request;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsBuilder;
import org.springframework.data.elasticsearch.core.facet.AbstractFacetRequest;
import org.springframework.util.Assert;

/**
 * Term facet
 *
 * @author Artur Konczak
 */
public class TermFacetRequest extends AbstractFacetRequest {

	private String[] fields;
	private String[] excludeTerms;
	private int size = 10;
	private TermFacetOrder order = TermFacetOrder.descCount;
	private boolean allTerms = false;
	private String regex = null;
	private int regexFlag = 0;

	public TermFacetRequest(String name) {
		super(name);
	}

	public void setFields(String... fields) {
		this.fields = fields;
	}

	public void setSize(int size) {
		Assert.isTrue(size >= 0, "Size should be bigger then zero !!!");
		this.size = size;
	}

	public void setOrder(TermFacetOrder order) {
		this.order = order;
	}

	public void setExcludeTerms(String... excludeTerms) {
		this.excludeTerms = excludeTerms;
	}

	public void setAllTerms(boolean allTerms) {
		this.allTerms = allTerms;
	}

	public void setRegex(String regex) {
		this.regex = regex;
	}

	public void setRegex(String regex, int regexFlag) {
		this.regex = regex;
		this.regexFlag = regexFlag;
	}

	@Override
	public TermsBuilder getFacet() {
		Assert.notEmpty(fields, "Please select at last one field !!!");
		TermsBuilder builder = AggregationBuilders.terms(getName()).field(fields[0]);
		switch (order) {

			case descTerm:
				builder.order(Terms.Order.term(false));
				break;
			case ascTerm:
				builder.order(Terms.Order.term(true));
				break;
			case ascCount:
				builder.order(Terms.Order.count(true));
				break;
			default:
				builder.order(Terms.Order.count(false));
		}
		if (ArrayUtils.isNotEmpty(excludeTerms)) {
			builder.exclude(excludeTerms);
		}

		// if (allTerms) {
		// builder.^
		// }

		if (StringUtils.isNotBlank(regex)) {
			builder.include(regex);
		}

		return builder;
	}
}
