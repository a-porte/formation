module "data" {
  source = "./../data"
}

variable "NB_INSTANCES" {
  default = 10
}

variable "IAM_USERS" {
  default = ["a", "b", "c"]
}

resource "aws_iam_user" "aws_users" {
  #count = length(var.IAM_USERS)
  #name = var.IAM_USERS[count.index]
  for_each = toset(var.IAM_USERS)
  name = each.value
  tags = {
    key = each.key
    value = each.value
  }
}

# Have not been able send key to EC2 because of a lack of authorisations
resource "aws_key_pair" "key_pair" {
  public_key = file("./terraform.pub")
  key_name =  terraform.workspace == "prod" ? "prod-key" : "terraform-key"
}

resource "aws_instance" "my_ec2_instance" {
  count = var.NB_INSTANCES #will create as many resources as asked
  #index can be retrieved this way count.index
  # has an impact on output variables
  timeouts { # appears to do nothing at all if a remote_exec is ongoing
    create = "50s"
  }
  key_name = aws_key_pair.key_pair.key_name
  #ami = var.AWS_AMIS[var.AWS_REGION] #change AMI according to region name
  ami = module.data.ami_id
  instance_type = "t2.nano"
  tags = {
    Name = format("%s-%s", module.data.random_name, count.index)
    #Name = "${terraform.workspace == "prod" ? "prod-ec2" : "test_ec2_with_terraform"}"
  }

  connection {
    type = "ssh"
    user = "ubuntu"
    private_key = file("./terraform")
    host = self.public_ip
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

  # provisioners should be used in last resort
  #https://developer.hashicorp.com/terraform/language/resources/provisioners/syntax
  #provisioner "local-exec" { # realizes a defined command on the machine where and when apply is executed
  # on_failure = continue # by default : fail
  #  command = "echo ${aws_instance.my_ec2_instance.public_ip} > ip_address.txt"
  #}

  #provisioner "local-exec" {
  #  when = destroy
  #  command = "rm -v ip_address.txt"
  #}

  #  provisioner "file" {
  # copies from <source> on the local machine to <destination> on the remote one
  #    source = "./to_copy.txt"
  #    destination = "/tmp"
  #  }

  #requires a connection
  # execute commands on the remote
  #provisioner "remote-exec" {
  #on_failure = continue

  #  inline = ["ls -lat /tmp"]
  #}
}

resource "aws_security_group" "secu_grp" {
  name = "${terraform.workspace == "prod" ? "prod-secu-group" : "terraform-secu-group"}"

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

  #required to send file via SSH
  ingress {
    from_port = 22
    to_port = 22
    protocol = "tcp"
    cidr_blocks = ["0.0.0.0/0"]

  }

  #  egress {
  #    from_port = 8
  #    to_port = 0
  #    protocol = "icmp"
  #    cidr_blocks = ["0.0.0.0/0"]
  #  } # CIDR blocks

  #  ingress {
  #    from_port = 8
  #    to_port = 0
  #    protocol = "icmp"
  #    cidr_blocks = ["0.0.0.0/0"]
  #  }
}

#resource "aws_s3_bucket" "my-ec3-bucket-11102023" {
#  tags = {
#    Name = "aws_s3_bucket.my-ec3-bucket-11102023"
#  }
#  bucket = "my-ec3-bucket-11102023"
#}
