provider "aws" {
  region = "eu-west-3"
  access_key = ""
  secret_key = ""
}

resource "aws_instance" "my_ec2_instance" {
  ami = "ami-0f82b13d37cd1e8cc"
  instance_type = "t2.nano"
  tags = {
    Name = "test_ec2_with_terraform"
  }

  user_data = <<-EOF
    #!/bin/bash
    sudo apt-get update
    sudo apt-get install -y apache2
    sudo systemctl start apache2
    sudo systemctl enable apache2
    sudo echo "<h1> Hello </h1>" > /var/www/html/index.html
  EOF

  # is a reference to the security group define right below
  vpc_security_group_ids = [aws_security_group.secu_grp.id]
  # creates a dependency
  # <provider>_<type>.<resource_name>.<field_type>
}

resource "aws_security_group" "secu_grp" {
  name = "terraform-secu-group"

  #this resource is required so that EC2 can receive external requests
  egress {
    from_port = 0
    to_port = 0
    protocol = "-1"
    cidr_blocks = ["0.0.0.0/0"]
   } # CIDR blocks

  ingress {
    from_port = 80
    to_port = 80
    protocol = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }
}