package com.vironit.kazimirov.dao.DaoInterface;

import com.vironit.kazimirov.entity.Subsection;

import java.util.List;

public interface SubsectionDao {
    void addSubsection(int id, String title);

    List<Subsection> showSubsections();
}
