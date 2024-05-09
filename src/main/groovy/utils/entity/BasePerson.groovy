package utils.entity

class BasePerson extends BaseEntity {

    String name
    String email
    String phone
    String cpfCnpj
    PersonType personType

    static constraints = {
        name blank: false
        email blank: false, email: true
        phone blank: false, size: 10..11
        cpfCnpj blank: false, size: 11..14
        personType blank: false
    }
}
