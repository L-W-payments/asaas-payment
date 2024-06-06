package com.miniasaaslw.domain.emailNotification

import com.miniasaaslw.entity.BaseEntity

class EmailNotification extends BaseEntity {

    String recipientEmail

    String subject

    String url

    String body

    Boolean isSent

}
