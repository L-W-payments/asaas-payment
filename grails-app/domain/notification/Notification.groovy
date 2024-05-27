package notification

import com.miniasaaslw.domain.customer.Customer
import com.miniasaaslw.entity.BaseEntity
import com.miniasaaslw.entity.enums.NotificationPriority

class Notification extends BaseEntity {

    Customer customer

    String title

    String message

    String url

    NotificationPriority priority
}
