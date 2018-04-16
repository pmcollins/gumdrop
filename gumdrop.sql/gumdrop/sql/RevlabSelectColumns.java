package gumdrop.sql;

import revlab.core.entities.RevlabEntity;

import java.util.function.Supplier;

public abstract class RevlabSelectColumns<T extends RevlabEntity> extends SelectColumns<T> {

  protected RevlabSelectColumns(Supplier<T> constructor) {
    super(constructor);
    add(new SelectIntegerColumn<>("id", RevlabEntity::setId));
    add(new SelectTimestampColumn<>("created_at", (entity, timestamp) -> entity.setCreatedAt(timestamp.toInstant())));
  }

}
