resource "aws_s3_bucket" "my_bucket" {
  bucket = "my-bucket-name-in-aws-console"
}

resource "aws_s3_bucket_ownership_controls" "my_ownership" {
  bucket = aws_s3_bucket.my_bucket.id
  rule {
    object_ownership = "BucketOwnerPreferred"
  }
}

resource "aws_s3_bucket_public_access_block" "my_access_block" {
  bucket = aws_s3_bucket.my_bucket.id

  block_public_acls = false
  block_public_policy = false
  ignore_public_acls = false
  restrict_public_buckets = false
}


resource "aws_s3_bucket_acl" "my_acl" {
  bucket = aws_s3_bucket.my_bucket.id
  depends_on = [
    aws_s3_bucket_ownership_controls.my_ownership,
    aws_s3_bucket_public_access_block.my_access_block
  ]
  acl = "private"
}

resource "aws_s3_bucket_website_configuration" "my_website_conf" {
  bucket = aws_s3_bucket.my_bucket.id
  index_document {
    suffix = "index.html"
  }
  error_document {
    key = "error.html"
  }
  routing_rules = <<EOF
[{
    "Condition": {
        "KeyPrefixEquals": "docs/"
    },
    "Redirect": {
        "ReplaceKeyPrefixWith": ""
    }
}]
EOF
}