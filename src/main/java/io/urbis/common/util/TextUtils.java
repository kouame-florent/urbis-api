/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Clob;
import java.sql.SQLException;

/**
 *
 * @author florent
 */
public class TextUtils {
    
    public static String getClobString(Clob clob) throws SQLException, IOException {
        BufferedReader stringReader = new BufferedReader(clob.getCharacterStream());
        String singleLine;
        StringBuilder strBuilder = new StringBuilder();
        while ((singleLine = stringReader.readLine()) != null) {
            strBuilder .append(singleLine);
        }
        return strBuilder.toString();
    }
}
