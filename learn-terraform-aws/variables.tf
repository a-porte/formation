variable "AWS_REGION" {
  #variable declaration, mandatory to be able to use a variable
  type = string #optional
  default = "eu-west-2" #mandatory
  description = "servers' region" #optional
}
variable "SECRET" {
  default = {
    "access_key" = ""
    "secret_key" = ""

  }
}
