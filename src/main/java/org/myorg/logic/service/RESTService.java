package org.myorg.logic.service;

import org.myorg.logic.Protocol;

import java.util.List;

public interface RESTService {

    public List<String> getHeader();

    public Protocol getProtocol();

    public String getResponse();

}
