/**
 * Copyright (c) 2002-2012 "Neo Technology,"
 * Network Engine for Objects in Lund AB [http://neotechnology.com]
 *
 * This file is part of Neo4j.
 *
 * Neo4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.neo4j.cypherdsl;

import org.neo4j.cypherdsl.query.AbstractExpression;
import org.neo4j.cypherdsl.query.Value;

/**
* Represents a literal value, such as a string or number.
*/
public class Literal
    extends Value
{
    Literal( Object value )
    {
        super( new LiteralExpression(value) );
    }

    private static class LiteralExpression
        extends AbstractExpression
    {
        public Object value;

        private LiteralExpression( Object value )
        {
            this.value = value;
        }

        @Override
        public void asString( StringBuilder builder )
        {
            if (value instanceof String)
            {
                if (builder.toString().endsWith( "/" ) || builder.toString().endsWith( "(?i)" )) // -> Regexp with /literal/ or /(?i)literal/
                    builder.append( value.toString().replaceAll( "/", "\\\\/" ) );
                else
                    builder.append( "\"" ).append( value.toString().replace( "\\", "\\\\" ).replace( "\"", "\\\"" ) ).append( "\"" );
            }
            else
                builder.append( value.toString() );
        }

        @Override
        public String toString()
        {
            return value.toString();
        }
    }
}