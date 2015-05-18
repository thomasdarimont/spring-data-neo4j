package org.springframework.data.neo4j.integration.extensions.domain;

import org.neo4j.ogm.annotation.typeconversion.Convert;
import org.neo4j.ogm.typeconversion.ByteArrayBase64Converter;

/**
 * @author: Vince Bickers
 */
public class User {

    private Long id;

    //@Convert(ByteArrayBase64Converter.class)
    private byte[] profilePictureData;

    public byte[] getProfilePictureData() {
        return this.profilePictureData;
    }

}
