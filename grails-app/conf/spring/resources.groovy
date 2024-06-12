import com.miniasaaslw.security.UserPasswordEncoderListener
import com.miniasaaslw.service.user.CustomUserDetailsService

// Place your Spring DSL code here
beans = {
    userPasswordEncoderListener(UserPasswordEncoderListener)
    userDetailsService(CustomUserDetailsService)
}
