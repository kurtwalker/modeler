/*!
 * PENTAHO CORPORATION PROPRIETARY AND CONFIDENTIAL
 *
 * Copyright 2002 - 2014 Pentaho Corporation (Pentaho). All rights reserved.
 *
 * NOTICE: All information including source code contained herein is, and
 * remains the sole property of Pentaho and its licensors. The intellectual
 * and technical concepts contained herein are proprietary and confidential
 * to, and are trade secrets of Pentaho and may be covered by U.S. and foreign
 * patents, or patents in process, and are protected by trade secret and
 * copyright laws. The receipt or possession of this source code and/or related
 * information does not convey or imply any rights to reproduce, disclose or
 * distribute its contents, or to manufacture, use, or sell anything that it
 * may describe, in whole or in part. Any reproduction, modification, distribution,
 * or public display of this information without the express written authorization
 * from Pentaho is strictly prohibited and in violation of applicable laws and
 * international treaties. Access to the source code contained herein is strictly
 * prohibited to anyone except those individuals and entities who have executed
 * confidentiality and non-disclosure agreements or other agreements with Pentaho,
 * explicitly covering such access.
 */

package org.pentaho.agilebi.modeler.models.annotations;

import org.pentaho.agilebi.modeler.ModelerWorkspace;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Rowell Belen
 */
public class ModelAnnotation<T extends AnnotationType> implements Serializable {

  private static final long serialVersionUID = 5742135911581602697L;

  private Action action;

  private String column;

  private T annotation;

  public ModelAnnotation() {
  }

  public ModelAnnotation( final Action action, final String column, final T annotation ) {
    setAction( action );
    setColumn( column );
    setAnnotation( annotation );
  }

  public Action getAction() {
    return action;
  }

  public void setAction( Action action ) {
    this.action = action;
  }

  public String getColumn() {
    return column;
  }

  public void setColumn( String column ) {
    this.column = column;
  }

  public T getAnnotation() {
    return annotation;
  }

  public void setAnnotation( T annotation ) {
    this.annotation = annotation;
  }

  /**
   * **** Utility methods ******
   */

  public static List<ModelAnnotation<Measure>> getMeasures(
      final List<ModelAnnotation<? extends AnnotationType>> annotations ) {
    return filter( annotations, Measure.class );
  }

  public static List<ModelAnnotation<Attribute>> getAttributes(
      final List<ModelAnnotation<? extends AnnotationType>> annotations ) {
    return filter( annotations, Attribute.class );
  }

  private static <S extends AnnotationType> List<ModelAnnotation<S>> filter(
      final List<ModelAnnotation<? extends AnnotationType>> annotations, Class<S> cls ) {

    List<ModelAnnotation<S>> list = new ArrayList<ModelAnnotation<S>>();
    if ( cls != null && annotations != null && annotations.size() > 0 ) {
      annotations.removeAll( Collections.singleton( null ) ); // remove nulls
      for ( ModelAnnotation<?> annotation : annotations ) {
        if ( annotation.getAnnotation() != null && cls.equals( annotation.getAnnotation().getClass() ) ) {
          list.add( (ModelAnnotation<S>) annotation );
        }
      }
    }

    return list;
  }

  public void apply( final ModelerWorkspace modelerWorkspace ) {
    annotation.apply( modelerWorkspace, getColumn() );
  }

  public AnnotationType.AnnotationSubType getType() {
    if ( annotation != null ) {
      return annotation.getType();
    }

    return null;
  }

  public Map<String, Serializable> describeAnnotation() {
    if ( annotation != null ) {
      return annotation.describe();
    }

    return null;
  }

  public void populateAnnotation( final Map<String, Serializable> properties ) {
    if ( annotation != null ) {
      annotation.populate( properties );
    }
  }

  public boolean isActionSupported( final Action action ) {
    if ( annotation != null ) {
      return annotation.isActionSupported( action );
    }
    return false;
  }

  public static enum Action {
    CREATE,
    UPDATE,
    REMOVE
  }

  public static enum TimeType {
    Regular,
    TimeYears,
    TimeHalfYears,
    TimeQuarters,
    TimeMonths,
    TimeWeeks,
    TimeDays,
    TimeHours,
    TimeMinutes,
    TimeSeconds,
    TimeUndefined,
    Null
  }

  public static enum GeoType {
    Lat_Long,
    Country,
    City,
    State,
    County,
    Postal_Code,
    Continent,
    Territory
  }
}