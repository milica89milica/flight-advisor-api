package com.htec.fa_api.util;

import com.htec.fa_api.model.Airport;
import com.htec.fa_api.model.Route;
import com.htec.fa_api.repository.RouteRepository;
import com.htec.fa_api.service.RouteService;
import es.usc.citius.hipster.algorithm.Hipster;
import es.usc.citius.hipster.graph.GraphBuilder;
import es.usc.citius.hipster.graph.GraphSearchProblem;
import es.usc.citius.hipster.graph.HipsterDirectedGraph;
import es.usc.citius.hipster.model.problem.SearchProblem;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Copyright (C) 2013-2018 Centro de Investigación en Tecnoloxías da Información (CITIUS) (http://citius.usc.es)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

@Component
public class GraphSearcher {

    private GraphBuilder<Integer, Double> builder;

    private final RouteService routeService;

    public GraphSearcher(RouteService routeService) {
        this.routeService = routeService;
    }

    public void defineConnections() {
        builder = GraphBuilder.create();
        List<Route> base = routeService.getAll();
        base.forEach(route -> {
            builder.connect(route.getSourceAirport().getOpenFlightId())
                    .to(route.getDestinationAirport().getOpenFlightId())
                    .withEdge(route.getPrice());
        });
    }

    public List<List<Integer>> search(Airport source, Airport destination) {
        HipsterDirectedGraph<Integer, Double> graph = builder.createDirectedGraph();
        SearchProblem p = GraphSearchProblem
                .startingFrom(source.getOpenFlightId())
                .in(graph)
                .takeCostsFromEdges()
                .build();
        return Hipster.createDijkstra(p).search(destination.getOpenFlightId()).getOptimalPaths();
    }
}




