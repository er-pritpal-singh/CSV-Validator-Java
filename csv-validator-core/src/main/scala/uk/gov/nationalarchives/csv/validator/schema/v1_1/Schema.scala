/**
 * Copyright (c) 2013, The National Archives <digitalpreservation@nationalarchives.gov.uk>
 * http://www.nationalarchives.gov.uk
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package uk.gov.nationalarchives.csv.validator.schema.v1_1

import org.apache.commons.io.FilenameUtils
import uk.gov.nationalarchives.csv.validator.metadata.Row
import uk.gov.nationalarchives.csv.validator.schema._

case class NoExt(value: ArgProvider) extends ArgProvider {

  def referenceValue(columnIndex: Int, row: Row, schema: Schema): Option[String] = value.referenceValue(columnIndex, row, schema).map(FilenameUtils.removeExtension(_))

  def toError = "noext(" + value.toError + ")"

}

case class Concat(s1: ArgProvider, s2: ArgProvider) extends  ArgProvider {

  def referenceValue(columnIndex: Int, row: Row, schema: Schema): Option[String] = concat(s1.referenceValue(columnIndex, row, schema) , s2.referenceValue(columnIndex, row, schema))

  def concat(ms1: Option[String], ms2 : Option[String]): Option[String] = (ms1,ms2) match {
    case (None, None) => None
    case _ => Some(ms1.getOrElse("") + ms2.getOrElse(""))
  }

  def toError = "concat(" + s1.toError+ ", " +  s2.toError + ")"
}

