package com.maxtrain.prs.requestline;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestlineRepository extends JpaRepository<Requestline, Integer> {
		List<Requestline> findRequstlineByRequestId(int requestId);
}
