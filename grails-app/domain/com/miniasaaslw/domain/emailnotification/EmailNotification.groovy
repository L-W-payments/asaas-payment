package com.miniasaaslw.domain.emailnotification

import com.miniasaaslw.entity.BaseEntity

class EmailNotification extends BaseEntity {

    String recipientEmail

    String subject

    String url

    String body

    Boolean sent

}
