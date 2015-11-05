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
package org.springframework.data.elasticsearch.core.facet;

import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.bucket.histogram.Histogram;
import org.elasticsearch.search.aggregations.bucket.range.Range;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket;
import org.elasticsearch.search.aggregations.metrics.NumericMetricsAggregation;
import org.springframework.data.elasticsearch.core.facet.result.Term;
import org.springframework.data.elasticsearch.core.facet.result.TermResult;

/**
 * @author Artur Konczak
 * @author Petar Tahchiev
 */
public class DefaultFacetMapper {

	public static FacetResult parse(Aggregation facet) {
		if (facet instanceof Terms) {
			return parseTerm((Terms) facet);
		}

		if (facet instanceof Range) {
			return parseRange((Range) facet);
		}

		if (facet instanceof NumericMetricsAggregation) {
			return parseStatistical((NumericMetricsAggregation) facet);
		}

		if (facet instanceof Histogram) {
			return parseHistogram((Histogram) facet);
		}

		return null;
	}

	private static FacetResult parseTerm(Terms facet) {
		List<Term> entries = new ArrayList<Term>();
		for (Bucket entry : facet.getBuckets()) {
			entries.add(new Term(entry.getKeyAsString(), (int) entry.getDocCount()));
		}
		return new TermResult(facet.getName(), entries, facet.getBuckets().size(), facet.getSumOfOtherDocCounts(),
				facet.getDocCountError());
	}

	private static FacetResult parseRange(Range facet) {

		// TODO:
		throw new UnsupportedOperationException("TODO: convert Range to facet result");
		// List<Range> entries = new ArrayList<Range>();
		// for (RangeFacet.Entry entry : facet.()) {
		// entries.add(new Range(entry.getFrom() == Double.NEGATIVE_INFINITY ? null : entry.getFrom(),
		// entry.getTo() == Double.POSITIVE_INFINITY ? null : entry.getTo(), entry.getCount(), entry.getTotal(), entry
		// .getTotalCount(), entry.getMin(), entry.getMax()));
		// }
		// return new RangeResult(facet..getName(), entries);
	}

	private static FacetResult parseStatistical(NumericMetricsAggregation facet) {

		// TODO:
		throw new UnsupportedOperationException("TODO: convert NumericMetricsAggregation to facet result");
		// return new StatisticalResult(facet.getName(), facet.getCount(), facet.getMax(), facet.getMin(), facet.getMean(),
		// facet.getStdDeviation(), facet.getSumOfSquares(), facet.getTotal(), facet.getVariance());
	}

	private static FacetResult parseHistogram(Histogram facet) {

		// TODO:
		throw new UnsupportedOperationException("TODO: convert Histogram to facet result");
		// List<IntervalUnit> entries = new ArrayList<IntervalUnit>();
		// for (Bucket entry : facet.getBuckets()) {
		// entries.add(new IntervalUnit(entry.getKey(), entry.getDocCount(), entry.(), entry.(), entry
		// .getMean(), entry.getMin(), entry.getMax()));
		// }
		// return new HistogramResult(facet.getName(), entries);
	}
}
