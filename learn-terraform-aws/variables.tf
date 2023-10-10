variable "AWS_REGION" {
  #variable declaration, mandatory to be able to use a variable
  type = string #optional
  default = "eu-west-2" #mandatory
  description = "servers' region" #optional
}
variable "SECRET" {}

variable "AWS_AMIS" {
  default = {
    "eu-west-2" = "ami-042fab99b38a3963d"
    "eu-west-3" = "ami-0f82b13d37cd1e8cc"
  }
}
